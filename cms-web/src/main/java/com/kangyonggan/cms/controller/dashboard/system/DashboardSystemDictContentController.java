package com.kangyonggan.cms.controller.dashboard.system;

import com.kangyonggan.cms.controller.BaseController;
import com.kangyonggan.cms.dto.Page;
import com.kangyonggan.cms.dto.Params;
import com.kangyonggan.cms.model.Dictionary;
import com.kangyonggan.cms.model.DictionaryType;
import com.kangyonggan.cms.service.DictionaryService;
import com.kangyonggan.cms.service.DictionaryTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 2017/1/9
 */
@Controller
@RequestMapping("dashboard/system/dict/content")
public class DashboardSystemDictContentController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    /**
     * 字典管理
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    public String index() {
        return getPathList();
    }

    /**
     * 字典列表查询
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    @ResponseBody
    public Page<Dictionary> list() {
        Params params = getRequestParams();
        List<Dictionary> dictionaries = dictionaryService.searchDictionaries(params);

        return new Page<>(dictionaries);
    }

    /**
     * 添加字典
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    public String create(Model model) {
        List<DictionaryType> dictionaryTypes = dictionaryTypeService.findAllDictionaryTypes();

        model.addAttribute("dictionary", new DictionaryType());
        model.addAttribute("dictionaryTypes", dictionaryTypes);
        return getPathFormModal();
    }

    /**
     * 保存字典
     *
     * @param dictionary
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    public Map<String, Object> save(@ModelAttribute("dictionary") @Valid Dictionary dictionary, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            dictionaryService.saveDictionary(dictionary);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑字典
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    public String create(@PathVariable("id") Long id, Model model) {
        List<DictionaryType> dictionaryTypes = dictionaryTypeService.findAllDictionaryTypes();

        model.addAttribute("dictionary", dictionaryService.findDictionaryById(id));
        model.addAttribute("dictionaryTypes", dictionaryTypes);
        return getPathFormModal();
    }

    /**
     * 更新字典
     *
     * @param dictionary
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    public Map<String, Object> update(@ModelAttribute("dictionary") @Valid Dictionary dictionary, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            dictionaryService.updateDictionary(dictionary);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 删除/恢复
     *
     * @param id
     * @param isDeleted
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/deleted/{isDeleted:\\b0\\b|\\b1\\b}", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    @ResponseBody
    public Map<String, Object> deleted(@PathVariable("id") Long id, @PathVariable("isDeleted") byte isDeleted) {
        Dictionary dictionary = dictionaryService.findDictionaryById(id);
        dictionary.setIsDeleted(isDeleted);
        dictionaryService.updateDictionary(dictionary);
        return super.getResultMap();
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/remove", method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_DICT_CONTENT")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable("id") Long id) {
        dictionaryService.deleteDictionaryById(id);
        return super.getResultMap();
    }

}