# Line Bot B3

## How to get your work, works with other people's
1. Please do not modify anything other than your work
2. Create class that extends abstractlinechathandlerdecorator
3. Create featureconfiguration.java class under the feature folder
4. @Autowire LineMessagingClient in your class constructor if you need additional functionality

## Example: EchoChatHandler
files: src/main/java/advprog/bot/feature/echo

### Handler class
Public methods in AbstractLineChatHandlerDecorator are template methods, please DO NOT override them to make sure your work works and doesn't break other people's works.
Why not just set them to be private? Because they are interface method exposed to controller.
```java
// this is for example purpose !!!
public class EchoChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(EchoChatHandler.class.getName());

    public EchoChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Echo chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/echo");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        return Collections.singletonList(
                new TextMessage(event.getMessage().getText().replace("/echo", ""))
        ); // just return list of TextMessage for multi-line reply!
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!
    }

    @Override
    protected boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }
    
    @Override
    protected boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }
}

```

### Configuration class
```java
@Configuration
public class EchoChatHandlerConfiguration {

    @Bean
    EchoChatHandler echoChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        EchoChatHandler handler = new EchoChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}

```
please follow exactly like this -- your handler class must decorate the existing handler

## Additional Line Functionality
If you need to get more things from Line, just add LineMessagingClient to your use case class (NOT chat handler class) like this example:
```java
    private final LineMessagingClient client;


    @Autowired
    public LineMessageReplyServiceImpl(LineMessagingClient client) {
        this.client = client;
    }
```

anyway if you still want to add LineMessagingClient to the chat handler class, just modify the configuration class:
```java
@Configuration
public class EchoChatHandlerConfiguration {

    @Bean
    EchoChatHandler echoChatHandler(BotController controller, LineMessagingClient client) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        EchoChatHandler handler = new EchoChatHandler(currenctChatHandler, client);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
```
DO NOT mix both way, pick only one: @Autowired or @Configuration + @Bean

Any other way than dependency injection is not recommended.


## Bonus: How to respond to an image, reply with an image, and reply multiple lines
Please check https://gitlab.com/adprog/B3/tree/controller-experiment2/src/main/java/advprog/bot/feature/bonus

## About AbstractLineChatHandlerDecorator
This class is basically the textbook real-life implementation of decorator pattern and template method pattern.
The base handler is added from the beginning, and student's handler will be added at runtime. BotController prevents anyone
from replacing the handler without decorating the currently existing one.


<sub>Document created at 12 May 2018 by Yudhistira Erlandinata</sub>

<sub>Document last modified at 13 May 2018 by Yudhistira Erlandinata</sub>
