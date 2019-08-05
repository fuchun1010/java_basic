package com.tank.cron;

import com.cronutils.mapper.CronMapper;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author fuchun
 */
public class CronHandler {


  @SneakyThrows
  public <R> Optional<R> handleCron(@NonNull final String cronStrExp,
                                    @NonNull final BiFunction<String, CronParser, R> cronHandler) {
    CronParser cronParser = this.initCronParser();
    if (Objects.isNull(cronParser)) {
      throw new NullPointerException("cronParser初始化异常");
    }
    Cron cron = cronParser.parse(cronStrExp);
    CronMapper cronMapper = CronMapper.sameCron(this.initCronDefinition());
    Cron rs = cronMapper.map(cron).validate();
    if (Objects.isNull(rs)) {
      throw new IllegalArgumentException(String.format("[%s]不是spring的cron表达式", cronStrExp));
    }
    R result = cronHandler.apply(cronStrExp, cronParser);
    return Optional.ofNullable(result);
  }


  private CronParser initCronParser() {
    CronDefinition cronDefinition = this.initCronDefinition();
    CronParser parser = new CronParser(cronDefinition);
    return parser;
  }

  private CronDefinition initCronDefinition() {
    CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING);
    return cronDefinition;
  }
}
