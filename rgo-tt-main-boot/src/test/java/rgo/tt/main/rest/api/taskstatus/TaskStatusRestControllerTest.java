package rgo.tt.main.rest.api.taskstatus;

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
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.*;

@SpringBootTest
@WebAppConfiguration
class TaskStatusRestControllerTest {

    @Autowired private WebApplicationContext context;
    @Autowired private DbTxManager tx;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
        mvc =  MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(get(TaskStatusRestController.BASE_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.taskStatuses", hasSize(STATUSES.size())))
                .andExpect(jsonPath("$.taskStatuses[0].entityId", is(TO_DO.getEntityId().intValue())))
                .andExpect(jsonPath("$.taskStatuses[0].name", is(TO_DO.getName())))
                .andExpect(jsonPath("$.taskStatuses[1].entityId", is(IN_PROGRESS.getEntityId().intValue())))
                .andExpect(jsonPath("$.taskStatuses[1].name", is(IN_PROGRESS.getName())))
                .andExpect(jsonPath("$.taskStatuses[2].entityId", is(DONE.getEntityId().intValue())))
                .andExpect(jsonPath("$.taskStatuses[2].name", is(DONE.getName())));

    }
}
