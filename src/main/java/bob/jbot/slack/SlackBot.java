package bob.jbot.slack;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

/**
 * A Slack Bot sample. You can create multiple bots by just extending {@link Bot} class like this
 * one.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
@JBot
@Profile("slack")
public class SlackBot extends Bot {

  private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

  private static List<String> holidays = new ArrayList<String>();

  private List<String> restaurants = new ArrayList<String>();

  private static Map<String, String> scheduleMap = new HashMap<>();

  private static List<String> yesList = new ArrayList<String>();

  private Integer ladderNum = 0;

  // mine G76UP7LMV
  // developer G5S41NDAN

  private static String CHANNEL_ID = "G5S41NDAN";

  /**
   * Slack token from application.properties file. You can get your slack token next
   * <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
   */
  @Value("${slackBotToken}")
  private String slackToken;

  @Override
  public String getSlackToken() {
    return slackToken;
  }

  @Override
  public Bot getSlackBot() {
    return this;
  }

  /**
   * Invoked when the bot receives a direct mention (@botname: message) or a direct message. NOTE:
   * These two event types are added by jbot to make your task easier, Slack doesn't have any direct
   * way to determine these type of events.
   *
   * @param session
   * @param event
   */
  @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
  public void onReceiveDM(WebSocketSession session, Event event) {
    reply(session, event, "안녕, 난 " + slackService.getCurrentUser().getName() + "이라고 해.");
  }

  /**
   * Invoked when bot receives an event of type message with text satisfying the pattern {@code ([a-z
   * ]{2})(\d+)([a-z ]{2})}. For example, messages like "ab12xy" or "ab2bc" etc will invoke this
   * method.
   *
   * @param session
   * @param event
   */
  // @Controller(events = EventType.MESSAGE, pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
  // public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
  // reply(session, event,
  // "First group: " + matcher.group(0) + "\n" + "Second group: " + matcher.group(1) + "\n" + "Third
  // group: " + matcher.group(2) + "\n" + "Fourth group: " + matcher.group(3));
  // }

  /**
   * Invoked when an item is pinned in the channel.
   *
   * @param session
   * @param event
   */
  // @Controller(events = EventType.PIN_ADDED)
  // public void onPinAdded(WebSocketSession session, Event event) {
  // reply(session, event, "Thanks for the pin! You can find all pinned items under channel
  // details.");
  // }

  /**
   * Invoked when bot receives an event of type file shared. NOTE: You can't reply to this event as
   * slack doesn't send a channel id for this event type. You can learn more about
   * <a href="https://api.slack.com/events/file_shared">file_shared</a> event from Slack's Api
   * documentation.
   *
   * @param session
   * @param event
   */
  // @Controller(events = EventType.FILE_SHARED)
  // public void onFileShared(WebSocketSession session, Event event) {
  // logger.info("File shared: {}", event);
  // }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Controller(pattern = "(안녕)")
  public void hello(WebSocketSession session, Event event) {
    reply(session, event, new Message("안녕, 난 " + slackService.getCurrentUser().getName() + "이라고 해."));
    stopConversation(event);
  }

  @Controller(pattern = "(밥내놔)")
  public void smallTalk1(WebSocketSession session, Event event) {
    reply(session, event, new Message("밥은 돈 주고 사먹어야 해.\n가끔 누가 사주면 땡큐지만"));
    stopConversation(event);
  }

  @Controller(pattern = "(밥줘)")
  public void smallTalk2(WebSocketSession session, Event event) {
    reply(session, event, new Message("밥은 돈 주고 사먹어야 해.\n가끔 누가 사주면 땡큐지만"));
    stopConversation(event);
  }

  @Controller(pattern = "(수고했어)")
  public void smallTalk3(WebSocketSession session, Event event) {
    reply(session, event, new Message("수고 많았어요"));
    stopConversation(event);
  }

  @Controller(pattern = "(누구)")
  public void smallTalk4(WebSocketSession session, Event event) {
    reply(session, event, new Message("아임 유어 파더."));
    stopConversation(event);
  }

  @Controller(pattern = "(띄어쓰기를 잘해야해)")
  public void smallTalk5(WebSocketSession session, Event event) {
    reply(session, event, new Message("말꼬리 잡는건 안좋아"));
    stopConversation(event);
  }

  @Controller(pattern = "(넬)")
  public void smallTalk6(WebSocketSession session, Event event) {
    reply(session, event, new Message("기억을 걷는 시간"));
    stopConversation(event);
  }

  @Controller(pattern = "(늦어요)")
  public void smallTalk7(WebSocketSession session, Event event) {
    reply(session, event, new Message("그냥 돌아가"));
    stopConversation(event);
  }

  @Controller(pattern = "(늦을거)")
  public void smallTalk8(WebSocketSession session, Event event) {
    reply(session, event, new Message("그냥 돌아가"));
    stopConversation(event);
  }

  @Controller(pattern = "(늦습니다)")
  public void smallTalk9(WebSocketSession session, Event event) {
    reply(session, event, new Message("그냥 돌아가"));
    stopConversation(event);
  }

  @Controller(pattern = "(지각)")
  public void smallTalk10(WebSocketSession session, Event event) {
    reply(session, event, new Message("그냥 돌아가"));
    stopConversation(event);
  }

  @Controller(pattern = "(명령어목록)")
  public void commandList(WebSocketSession session, Event event) {
    reply(session, event, new Message("밥집 추천\n밥집 목록\n밥집 추가\n밥집 제거\n사다리 게임\n띄어쓰기를 잘해야해"));
    stopConversation(event);
  }

  @Controller(pattern = "(밥집 추천)")
  public void recommendRestaurant(WebSocketSession session, Event event) {
    reply(session, event, new Message(restaurantText()));
    stopConversation(event);
  }

  @Controller(pattern = "(밥집 목록)")
  public void listRestaurant(WebSocketSession session, Event event) {
    reply(session, event, new Message(restaurantListText()));
    stopConversation(event);
  }

  @Controller(pattern = "(밥집 추가)", next = "addConfirmRestaurant")
  public void addRestaurant(WebSocketSession session, Event event) {
    startConversation(event, "addConfirmRestaurant");
    reply(session, event, new Message("추가하실 밥집 이름이 뭔가요?"));
  }

  @Controller(pattern = "(밥집 제거)", next = "removeConfirmRestaurant")
  public void removeRestaurant(WebSocketSession session, Event event) {
    startConversation(event, "removeConfirmRestaurant");
    reply(session, event, new Message("제거하실 밥집 이름이 뭔가요?"));
  }

  @Controller(next = "askListRestaurant")
  public void addConfirmRestaurant(WebSocketSession session, Event event) {
    if (event.getText().contains("취소")) {
      reply(session, event, new Message("취소할게요"));
      stopConversation(event);

    } else {
      String text = StringUtils.replace(event.getText(), "&amp;", "&");

      restaurants.add(text);
      reply(session, event, new Message("[ " + text + " ]\n처리 되었습니다.\n밥집 목록을 보여드릴까요?"));
      nextConversation(event);
    }
  }

  @Controller(next = "askListRestaurant")
  public void removeConfirmRestaurant(WebSocketSession session, Event event) {
    if (event.getText().contains("취소")) {
      reply(session, event, new Message("취소할게요"));
      stopConversation(event);

    } else {
      String text = StringUtils.replace(event.getText(), "&amp;", "&");

      boolean isRemove = restaurants.remove(text);

      if (isRemove) {
        reply(session, event, new Message("[ " + text + " ]\n처리 되었습니다.\n밥집 목록을 보여드릴까요?"));

      } else {
        reply(session, event, new Message("[ " + text + " ] 라는 식당은 없어요.\n밥집 목록을 보여드릴까요?"));
      }
      nextConversation(event);

    }
  }

  @Controller
  public void askListRestaurant(WebSocketSession session, Event event) {

    if (yesList.stream().anyMatch(s -> event.getText().contains(s))) {
      reply(session, event, new Message(restaurantListText()));
    } else {
      reply(session, event, new Message("네"));
    }

    stopConversation(event);
  }

  @Controller(pattern = "(사다리 게임)", next = "setNumberForLadder")
  public void startLadder(WebSocketSession session, Event event) {
    startConversation(event, "setNumberForLadder");
    reply(session, event, new Message("참여 인원을 알려주세요"));
  }

  @Controller(next = "askReadyForLadder")
  public void setNumberForLadder(WebSocketSession session, Event event) {
    Integer num = Integer.valueOf(event.getText());
    if (num == null) {
      reply(session, event, new Message("숫자가 아니네요"));
      stopConversation(event);

    } else {
      ladderNum = num;
      reply(session, event, new Message("각자 숫자를 하나씩 정해주세요\n준비되셨나요?"));
      nextConversation(event);
    }
  }

  @Controller
  public void askReadyForLadder(WebSocketSession session, Event event) {

    if (yesList.stream().anyMatch(s -> event.getText().contains(s))) {
      reply(session, event, new Message(ladderNumText()));
    } else {
      reply(session, event, new Message("준비가 되시면 다시 불러주세요."));
    }

    stopConversation(event);
  }

  private String ladderNumText() {
    int rnd = new Random().nextInt(ladderNum);

    return "당첨자는 " + rnd + "번 입니다.";
  }

  private String restaurantListText() {
    Collections.shuffle(restaurants);

    return "현재 등록된 밥집 목록은\n" + String.join("\n", restaurants) + "\n입니다.";
  }

  private String restaurantText() {
    return "오늘의 추천 식당은 `" + getRestaurant() + "` 입니다.";
  }

  private String coffeeText() {
    return "커피 한 잔 하시고 일하시는 건 어때요?";
  }

  private void sendMessage(WebSocketSession session, Event event, String channelId, String text) {

    Message message = new Message(text);
    message.setType(EventType.MESSAGE.name().toLowerCase());
    message.setChannel(channelId);
    try {
      session.sendMessage(new TextMessage(message.toJSONString()));

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) {

    yesList.add("응");
    yesList.add("네");
    yesList.add("그래");
    yesList.add("보여줘");
    yesList.add("웅");
    yesList.add("ㅇㅇ");
    yesList.add("넹");
    yesList.add("넵");

    // 2018 공휴일 임시
    holidays.add("20180501");
    holidays.add("20180507");
    holidays.add("20180522");
    holidays.add("20180606");
    holidays.add("20180613");
    holidays.add("20180815");
    holidays.add("20180924");
    holidays.add("20180925");
    holidays.add("20180926");
    holidays.add("20181003");
    holidays.add("20181009");
    holidays.add("20181225");

    restaurants.add("8000원 쌈밥");
    restaurants.add("지하1층에서시켜먹기");

    restaurants.add("중앙해장");
    restaurants.add("크라이치즈버거");
    restaurants.add("이태리 부대찌개");
    restaurants.add("초량돼지");
    restaurants.add("임고집 2층");
    restaurants.add("록스플레이트");
    restaurants.add("조선팔도");
    // restaurants.add("KT&G구내식당");
    restaurants.add("후레쉬빌");
    // restaurants.add("김밥천국");
    restaurants.add("요리왕");
    restaurants.add("카레마치");
    restaurants.add("삼군김치찌개");
    restaurants.add("남산옥");
    restaurants.add("소공동뚝배기");
    restaurants.add("길농원");
    restaurants.add("희래등");
    restaurants.add("명동할머니국수");
    // restaurants.add("치찌");
    restaurants.add("우리집");
    restaurants.add("명동칼국수");

    scheduleMap.put("1250", restaurantText());
    scheduleMap.put("1340", "식후 커피 타임을 생각하고 계시다면 사다리 타기는 어떠신가요?\n원하시면 '사다리 게임'이라고 입력해 주세요.");
    scheduleMap.put("1530", "커피 한 잔 하시고 일하시는 건 어때요?\n사다리 타기를 원하시면 '사다리 게임'이라고 입력해 주세요.");
//    scheduleMap.put("0950", "스프링캠프2018 신청 10분 전입니다.\nhttp://travel.coupang.com/np/products/3013816730");

    Collections.shuffle(restaurants);

    setScheduleTime(session);

    super.afterConnectionEstablished(session);
  }



  private void setScheduleTime(WebSocketSession session) {

    TimerTask task = new TimerTask() {
      @Override
      public void run() {

        String nowDate = getTextDate(new Date(), "HHmm");
        String msg = scheduleMap.get(nowDate);

        if (StringUtils.isEmpty(msg) || isHoliday()) {
          return;
        }

        sendMessage(session, new Event(), CHANNEL_ID, msg);
      }
    };


    Timer timer = new Timer();
    timer.schedule(task, new Date(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
  }

  private boolean isHoliday() {
    Date today = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(today);

    String formatDate = getTextDate(today, "yyyyMMdd");

    boolean isHoliday = false;

    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    if (Calendar.SUNDAY == dayOfWeek || Calendar.SATURDAY == dayOfWeek || holidays.contains(formatDate)) {
      isHoliday = true;
    }

    return isHoliday;
  }

  private String getRestaurant() {
    int rnd = new Random().nextInt(restaurants.size());
    return restaurants.get(rnd);
  }

  private String getTextDate(Date date, String format) {
    DateFormat df = new SimpleDateFormat(format);
    return df.format(date);
  }
}
