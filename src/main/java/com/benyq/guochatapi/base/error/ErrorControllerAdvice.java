package com.benyq.guochatapi.base.error;

import com.benyq.guochatapi.orm.entity.Result;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorControllerAdvice {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Benyq");
    }

    /**
     * 自定义异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> errorHandler(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", -2);
        map.put("errorMsg", ex.getMessage());
        ex.printStackTrace();
        return map;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> errorHandler(NoHandlerFoundException ex) {
        ex.printStackTrace();
        return Result.error(404, "服务异常");
    }

    /**
     * 自定义异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ChatException.class)
    public Map<String, Object> errorHandler(ChatException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", ex.getErrorCode());
        map.put("errorMsg", ex.getMessage());
        return map;
    }

    /**
     * 参数缺失异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Map<String, Object> errorHandler(MissingServletRequestParameterException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", -1);
        map.put("errorMsg", ex.getMessage());
        return map;
    }

    /**
     * 参数缺失异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String, Object> errorHandler(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            sb.append(error.getDefaultMessage()).append(",");
        });
        map.put("errorCode", -1);
        map.put("errorMsg", sb.toString());
        return map;
    }

}
