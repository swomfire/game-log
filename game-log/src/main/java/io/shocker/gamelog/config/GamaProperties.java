package io.shocker.gamelog.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("gama.properties")
public class GamaProperties {

    @Value("${gama.pageSize}")
    private Integer itemPageSize;

    @Value("${gama.source}")
    private String sourcePath;

    @Value("${gama.jaxbModelPath}")
    private String jaxbModelPath;

    @Value("${crawler.game.pageSize}")
    private Integer gameCrawlerPageSize;

    public Integer getItemPageSize() {
        return itemPageSize;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getJaxbModelPath() {
        return jaxbModelPath;
    }

    public Integer getGameCrawlerPageSize() {
        return gameCrawlerPageSize;
    }
}
