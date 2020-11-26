package com.springboot.web.login.oauth.social.facebook;

import com.springboot.web.login.oauth.social.SocialService;
import com.springboot.web.login.oauth.social.userconnection.UserConnection;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

//OAuth2ClientAuthenticationProcessingFilter 클래스를 상속 받아서 구현한 클래스입니다.security config 파일에서 필터 등록 해둠
public class FacebookOAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

    private ObjectMapper mapper = new ObjectMapper();
    private SocialService socialService;

    public FacebookOAuth2ClientAuthenticationProcessingFilter(SocialService socialService) {
        super("/login/facebook");
        this.socialService = socialService;
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //successfulAuthentication 메서드는 소셜 인증을 성공되면 호출되는 메서드입니다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //restTemplate.getAccessToken() 메서드를 통해서 해당 유저의 access token 정보를 가져옵니다.
        final OAuth2AccessToken accessToken = restTemplate.getAccessToken();

        //인자로 넘겨받은 authResult 객체에 소셜에서 넘겨받은 정보를 details 객체에 넘겨받습니다.
        //이때 넘겨받은 정보를 ObejctMapper를 이용해서 FacebookUserDetails로 변환 합니다.
        final OAuth2Authentication auth = (OAuth2Authentication) authResult;
        final Object details = auth.getUserAuthentication().getDetails(); // 소셜에서 넘겨 받은 정보를 details에 저장
        final FacebookUserDetails userDetails = mapper.convertValue(details, FacebookUserDetails.class); // Object mapper를 이용해서 객체 변환
        userDetails.setAccessToken(accessToken); // access token 정보도 저장

        String url = "https://graph.facebook.com/me?fields=name,email&access_token=" + accessToken;
        String email = "";

        try {
            JSONObject json = readJsonFromUrl(url);
            System.out.println(json.toString());
            System.out.println(json.get("email"));
            email = (String) json.get("email");
        } catch (Exception e) {

        }
        userDetails.setEmail(email);

        final UserConnection userConnection = UserConnection.valueOf(userDetails); // UserConnection를 userDetails 기반으로 생성
        final UsernamePasswordAuthenticationToken authenticationToken = socialService.doAuthentication(userConnection); // SocialService를 이용해서 인증 절차 진행

        super.successfulAuthentication(request, response, chain, authenticationToken);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


}
