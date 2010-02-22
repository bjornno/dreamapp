package integration.rest;

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

    @RequestMapping(method = RequestMethod.POST)    
    @Transactional
    public String postResource(@RequestBody MultiValueMap<String, String> params) {
        int id = jdbcTemplate.update("insert into recources(text) values(?)", params.get("text"));
        return "/" + id; 
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getResource(final @PathVariable String id) {
        return jdbcTemplate.queryForObject("select text from recources where id = " + id, String.class);
    }
}
