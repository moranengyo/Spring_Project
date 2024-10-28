package bitc.fullstack405.bitcteam3prj.config;

import bitc.fullstack405.bitcteam3prj.inteceptor.LoginCheck;
import bitc.fullstack405.bitcteam3prj.utils.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

  private final FileUtil fileUtil;

  public WebMVCConfig(FileUtil fileUtil) {
    this.fileUtil = fileUtil;
  }

  //  인터셉터, excludePathPatterns()로 로그인 필요없는 뷰페이지 추가 가능
//  현재 세션 유지시간 1시간
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheck()).addPathPatterns("/loginSuccess"); // 로그인 필요 페이지 추가 가능
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**")
//                .addResourceLocations("file:///C:/fullstack405/img/");


    //mac, linux 는 file://
    String path1 = "file:///" + fileUtil.getSaveFilePath();

    registry.addResourceHandler("/imgs/**")
            .addResourceLocations(path1);


  }
}
