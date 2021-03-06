package web.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class DemoController {


    @RequestMapping(value = "mytest.do", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getPostList() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "test");
        return map;
    }

    @RequestMapping(value = "demo", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> demo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("username"+username+" age "+age);
        map.put("username",username);
        map.put("age",age);
        return  map;
    }

    @RequestMapping(value = "demo/json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> demojson(@RequestParam String username, @RequestParam String age) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(username);
        map.put("username", "new"+username);
        return  map;
    }


    /**
     * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
     *
     * @param response
     * @param data
     */
    private void renderData(HttpServletResponse response, String data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);
        } catch (IOException ex) {
            Logger.getLogger(DemoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }


    private class UserDemo {
        private String username;
        private int age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
