package com.github.fashionbrot.ribbon;

import com.github.fashionbrot.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Server {

    private String host;

    private int port = 80;

    private String path ;

    public String getServer(){

        if (StringUtil.isNotEmpty(host) && host.startsWith("https")){
            return host+":"+port;
        }else if (StringUtil.isNotEmpty(host) && host.startsWith("http")){
            return host+":"+port;
        }
        return "http://"+host+":"+port;
    }


}
