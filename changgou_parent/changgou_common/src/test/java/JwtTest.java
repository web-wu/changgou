import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * @PROJECT_NAME: changgou
 * @USER: tabwu
 * @DATE: 2021/10/27 14:18
 * @DESCRIPTION:
 */
public class JwtTest {

    @Test
    public void jwtTest() {
        JwtBuilder builder = Jwts.builder()
                .setId("123456")
                .setSubject("tabwu")
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1800))
                .signWith(SignatureAlgorithm.HS256,"tabwu");

        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("username","zhangsan");
        userInfo.put("password","666888");
        userInfo.put("role","admin,editor");

        builder.addClaims(userInfo);

        System.out.println(builder.compact());
    }

    @Test
    public void parse() {
        String str = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwYWFlOGNkYy1hYzkwLTQ2OTAtYTE2MS02NzhiYjY0ODA4YjYiLCJzdWIiOiJ7XCJyb2xlXCI6XCJhZG1pbixlZGl0b3JcIixcInVzZXJuYW1lXCI6XCJoZWltYVwifSIsImlzcyI6ImFkbWluIiwiaWF0IjoxNjM1MzI0MzY5LCJleHAiOjE2MzUzMjc5Njl9.ZvXySDmzjhn1nx_v2SDFQwHKV70B8h0q-ZvPIFHapg4";

        Claims parser = Jwts.parser()
                .setSigningKey("com.tabwu")
                .parseClaimsJws(str)
                .getBody();

        System.out.println(parser);
    }
}
