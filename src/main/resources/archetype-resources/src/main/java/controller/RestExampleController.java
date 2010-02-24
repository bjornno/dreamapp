package ${groupId}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@Controller
@RequestMapping("/resource")
@Transactional
public class RestExampleController {
    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)    
    @Transactional
    public String postResource(String text) {
        jdbcTemplate.execute("insert into resources(text) values('" + text +"')");
        int id = jdbcTemplate.queryForInt("select id from resources where text = '" + text + "'");
        return "redirect:" + id;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getResource(final @PathVariable String id) {
        return jdbcTemplate.queryForObject("select text from resources where id = " + id, String.class);
    }
}
