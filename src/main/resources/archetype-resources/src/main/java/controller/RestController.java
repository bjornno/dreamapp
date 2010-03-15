package ${groupId}.controller;

import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@Controller
@RequestMapping("/resource")
@Transactional
public class RestController {
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String getAllResourceIds() {
        List<String> allIds = jdbcTemplate.queryForList("select id from resources", String.class);
        String result = "";
        for (int i = 0; i < allIds.size(); i++) {
            result += allIds.get(i) +"\n";
        }
        return result;
    }

    @Required
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)    
    @Transactional
    public String postResource(String text) {
        String id = UUID.randomUUID().toString();
        jdbcTemplate.execute("insert into resources(id, text) values('" + id + "', '" + text +"')");
        return "redirect:" + id;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getResource(final @PathVariable String id) {
        return jdbcTemplate.queryForObject("select text from resources where id = '" + id + "'", String.class);
    }
}
