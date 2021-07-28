package com.github.fashionbrot.system;

import com.github.fashionbrot.config.annotation.EnableSystemDynamicConfig;
import org.springframework.stereotype.Component;

@Component
@EnableSystemDynamicConfig(appCode = "app",envCode = "beta",serverAddress = "localhost:8081")
public class SystemConfig {


}
