package rgo.tt.main.rest.api.tasktype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.utils.H2PersistenceUtils;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.BUG;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.TASK;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.TYPES;

@SpringBootTest
@WebAppConfiguration
class TaskTypeRestControllerTest {

    @Autowired private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        H2PersistenceUtils.truncateTables();
        mvc =  MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get(TaskTypeRestController.BASE_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.types", hasSize(TYPES.size())))
                .andExpect(jsonPath("$.types[0].entityId", is(TASK.getEntityId().intValue())))
                .andExpect(jsonPath("$.types[0].name", is(TASK.getName())))
                .andExpect(jsonPath("$.types[1].entityId", is(BUG.getEntityId().intValue())))
                .andExpect(jsonPath("$.types[1].name", is(BUG.getName())));

    }
}
