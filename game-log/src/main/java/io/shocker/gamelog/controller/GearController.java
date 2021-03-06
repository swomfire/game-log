package io.shocker.gamelog.controller;

import io.shocker.gamelog.config.SpecEnum;
import io.shocker.gamelog.model.Categories;
import io.shocker.gamelog.model.Gears;
import io.shocker.gamelog.service.GameService;
import io.shocker.gamelog.service.GearService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping(value = "/gear")
public class GearController {
    private static final Logger logger = LogManager.getLogger(GearController.class);

    private final GearService gearService;

    public GearController(GearService gearService) {
        this.gearService = gearService;
    }

    @GetMapping(value = {""}, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Gears getGearList(
            @RequestParam(value = "nameLike", required = false) String nameLike
            , @RequestParam(value = "currentPage", required = false) Integer currentPage
            , @RequestParam(value = "categoryId", required = false) String categoryId) {
        Integer parsedCategoryId = null;
        try {
            parsedCategoryId = Integer.parseInt(categoryId);
        } catch (NumberFormatException ex) {
            logger.log(Level.WARN, "getGearList no value for categoryId");
        }
        Gears gears = this.gearService.getAllGears(nameLike, currentPage, parsedCategoryId);
        return gears;
    }

    @GetMapping(value = {"/spec"})
    @ResponseBody
    public Gears.Gear getGearDetail(@RequestParam(value = "id") Integer gearId) {
        return this.gearService.getGearDetail(gearId);
    }

    @GetMapping(value = "/load")
    @ResponseBody
    public String loadGames() {
        return this.gearService.crawlGear("gearCrawler");
    }

    @GetMapping(value = "/stop")
    @ResponseBody
    public Integer[] stopThread(@RequestParam(value = "name") String threadName) {
        return this.gearService.crawlingStatus(threadName, true);
    }


    @GetMapping(value = "/status")
    @ResponseBody
    public Integer[] threadStatus(@RequestParam(value = "name") String threadName) {
        return this.gearService.crawlingStatus(threadName, false);
    }

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Categories getAll() {
        Categories categories = this.gearService.getAllCategories();
        return categories;
    }

    @GetMapping(value = "/category/load")
    @ResponseBody
    public int loadGameCategories() {
        return this.gearService.crawlGearCategory();
    }


    @GetMapping(value = "/type/os")
    @ResponseBody
    public List<String> loadGameOsType() {
        return this.gearService.getSpecTypeData(SpecEnum.OS);
    }


    @GetMapping(value = "/type/ram")
    @ResponseBody
    public List<String> loadGameRamType() {
        return this.gearService.getSpecTypeData(SpecEnum.Ram);
    }


    @GetMapping(value = "/type/cpu")
    @ResponseBody
    public List<String> loadGameCpuType() {
        return this.gearService.getSpecTypeData(SpecEnum.CPU);
    }


    @GetMapping(value = "/type/gpu")
    @ResponseBody
    public List<String> loadGameGpuType() {
        return this.gearService.getSpecTypeData(SpecEnum.GPU);
    }
}
