package com.github.fashionbrot.api;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckForUpdateVo {

    /**
     * result code
     */
    private String resultCode;
    /**
     * file version
     */
    private String version;
    /**
     * for update file
     */
    private List<String> updateFiles;

    private String serverIp;
}
