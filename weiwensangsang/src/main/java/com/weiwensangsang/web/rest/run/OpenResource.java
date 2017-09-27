package com.weiwensangsang.web.rest.run;

import com.codahale.metrics.annotation.Timed;
import com.itranswarp.compiler.JavaStringCompiler;
import com.weiwensangsang.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.Map;

import static com.weiwensangsang.domain.ResponseMessage.message;

@RestController
@RequestMapping("/api")
public class OpenResource {

    private final Logger log = LoggerFactory.getLogger(OpenResource.class);

    @PostMapping("/use/compiler/java")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity compiler(@Valid @RequestBody String code) throws Exception {
        JavaStringCompiler compiler = new JavaStringCompiler();
        String javaCode = "package on.the.fly;\n" +
                "public class TryTest" + " {\n" + code + "}\n";
        try {
            Map<String, byte[]> results = compiler.compile("TryTest.java", javaCode);
            Class<?> clazz = compiler.loadClass("on.the.fly.TryTest", results);
            Method method = clazz.getMethod("run");
            Object o = clazz.newInstance();
            return ResponseEntity.ok(message(method.invoke(o).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(message(e.getMessage()));
        }
    }
}
