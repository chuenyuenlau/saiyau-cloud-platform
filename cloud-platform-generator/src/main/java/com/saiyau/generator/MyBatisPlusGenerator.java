package com.saiyau.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @author liuzhongyuan
 * @ClassName MyBatisPlusGenerator.java
 * @Description mybatis-plus代码生成器
 * @createTime 2021/10/14
 */
public class MyBatisPlusGenerator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "!");
    }

    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle("mybatisplus-config");
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //文件生成位置
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "/src/main/java";
        globalConfig.setOutputDir(outputDir);
        //是否覆盖文件
        globalConfig.setFileOverride(true);
        globalConfig.setAuthor("liuzhongyuan");
        //是否打开资源管理器
        globalConfig.setOpen(false);
        globalConfig.setActiveRecord(false);
        //开启XML二级缓存
        globalConfig.setEnableCache(false);
        //是否生成BaseResultMap
        globalConfig.setBaseResultMap(true);
        //是否生成BaseColumnList
        globalConfig.setBaseColumnList(true);
        //controller命名规则
        globalConfig.setControllerName("%sController");
        //service命名规则(去掉I开头)
        globalConfig.setServiceName("%sService");
        //serviceImpl命名规则(去掉I开头)
        globalConfig.setServiceImplName("%sServiceImpl");
        //mapper命名规则
        globalConfig.setMapperName("%sMapper");
        //xml命名规则
        globalConfig.setXmlName("%sMapper");
        //生成swagger 2注解
        globalConfig.setSwagger2(true);
        //date时间类型(默认LocalDateTime)
        //globalConfig.setDateType(DateType.ONLY_DATE);
        autoGenerator.setGlobalConfig(globalConfig);

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvertImpl());
        dataSourceConfig.setDriverName(rb.getString("jdbc.driver"));
        dataSourceConfig.setUsername(rb.getString("jdbc.username"));
        dataSourceConfig.setPassword(rb.getString("jdbc.password"));
        dataSourceConfig.setUrl(rb.getString("jdbc.url"));

        autoGenerator.setDataSource(dataSourceConfig);

        //包名配置
        PackageConfig packageConfig = new PackageConfig();
        //模块名
        packageConfig.setModuleName(scanner("模块名"));
        packageConfig.setParent(scanner("父包名"));
        packageConfig.setController("controller");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setEntity("domain.entity");
        packageConfig.setMapper("mapper");

        autoGenerator.setPackageInfo(packageConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //表名前缀
        strategyConfig.setTablePrefix("t_");
        //表名生成策略
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        strategyConfig.setEntitySerialVersionUID(true);

        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSuperEntityClass("com.saiyau.common.base.BaseEntity");
        strategyConfig.setInclude(scanner("表名，多个表名用英文逗号分隔").split(","));

        autoGenerator.setStrategy(strategyConfig);

        String templatePath = "/templates/mapper.xml.ftl";

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        //自定义输出配置
        List<FileOutConfig> foList = new ArrayList<>();
        foList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resuorces/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(foList);
        autoGenerator.setCfg(injectionConfig);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        autoGenerator.setTemplate(templateConfig);

        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        autoGenerator.execute();
    }

}
