package com.github.fashionbrot.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.model.LoginModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class JwtTokenUtil {

    /**
     * token秘钥，请勿泄露，请勿随便修改
     */
    private static final String SECRET = "+f+KCxF5UVl+O1a+Ab0EETlPv9aml/Iu16IWeIPVXbMp5M8bOvqj1VEmGoB7IEn+";

    public static final String USER_ID = "userId";
    public static final String REAL_NAME = "realName";
    public static final String ROLE_NAME = "roleName";
    public static final String ROLE_ID = "roleId";

    /**
     * token 过期时间: 30min
     */
    private static final int CALENDAR_FIELD = Calendar.MINUTE;
    private static final int CALENDAR_INTERVAL = 30;

    public static void main(String[] args) {
        String token = JwtTokenUtil.createToken(11L,"",1L,"",true);
        System.out.println(token);
        /*Integer userId = JwtTokenUtil.verifyTokenAndGetUser(token);
        System.out.println(userId);*/
    }

    private JwtTokenUtil() {
    }

    /**
     * 解密Token
     *
     * @param token token
     * @return payload
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error("token decode exception{}", e.getMessage());
            throw new MarsException(RespEnum.NEED_LOGIN);
        }
        return jwt.getClaims();
    }


    /**
     * 创建token
     *
     * @return token
     */
    public static String createToken(long userId,String realName,Long roleId,String roleName,boolean superAdmin) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(CALENDAR_FIELD, CALENDAR_INTERVAL);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<String, Object>();
        // 声明加密算法
        map.put("alg", "HS256");
        // 类型
        map.put("typ", "JWT");

        // build token
        // JWT构成: header, payload, signature
        return JWT.create()
                // header
                .withHeader(map)
                // 签发者
                .withClaim("iss", "Service")
                // jwt接收方
                .withClaim("aud", "mars")
                // 用户id
                .withClaim(USER_ID, userId)
                .withClaim(REAL_NAME,realName)
                .withClaim(ROLE_NAME, roleName)
                .withClaim(ROLE_ID,roleId)
                .withClaim("superAdmin",superAdmin)
                // sign time
                .withIssuedAt(iatDate)
                // expire time
                .withExpiresAt(expiresDate)
                // signature
                .sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * 验证解析token
     *
     * @param token token
     * @return user
     */
    public static LoginModel getLogin(String token) {
        Map<String, Claim> claimMap = verifyToken(token);
        Claim userIdClaim = claimMap.get(USER_ID);
        if (userIdClaim == null || userIdClaim.asInt() == null) {
            // token 校验失败, 抛出Token验证非法异常
            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
        }
        Claim roleName = claimMap.get(REAL_NAME);

        Claim realName = claimMap.get(REAL_NAME);
        if (realName == null || realName.asString() == null) {
            // token 校验失败, 抛出Token验证非法异常
            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
        }
        Claim superAdmin = claimMap.get("superAdmin");
        if (superAdmin == null || superAdmin.asBoolean() == null) {
            // token 校验失败, 抛出Token验证非法异常
            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
        }

        LoginModel model= LoginModel.builder()
                    .userId(userIdClaim.asLong())
                    .userName(realName.asString())
                    .superAdmin(superAdmin.asBoolean())
                    .build();
        if (roleName!=null){
            Claim claim = claimMap.get(ROLE_ID);
            if (claim!=null) {
                model.setRoleId(claim.asLong());
            }
            model.setRoleName(roleName.asString());
        }
        return model;
    }
}
