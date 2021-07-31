package com.github.fashionbrot.ribbon;

import com.github.fashionbrot.ribbon.util.StringUtil;
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

    public String getPath(){
        if (StringUtil.isNotEmpty(path) && path.startsWith("/")){
            return getServer()+path;
        }
        return getServer()+"/"+path;
    }

}
