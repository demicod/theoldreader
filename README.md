# theoldreader
[The Old Reader](https://theoldreader.com) API library for Kotlin

## Build
```bash
gradlew jar
```

## Usage
### Kotlin
```kotlin
val config = Config("https://theoldreader.com", "Custom Client Name")
val theoldreader = Theoldreader(config)

with(theoldreader) {
    // get current server status
    val status = status()
    
    // login and get token
    val clientLogin = auth.login(ClientLoginRequest("somebody@somewhere.net", "password"))
    val token = clientLogin!!.Auth
    
    // get user info
    val userInfo = user.info(BaseRequest(token))
    
    // get subscriptions
    val subscriptions = subscriptions.subscriptionList(BaseRequest(token))
    
    // get unread items ids
    val itemIds = items.allItemIds(ItemRequest(token = token, unread = true))
    
    // get folders
    val folders = folders.folderList(BaseRequest(token))
    
    // get friends
    val friends = friends.friendList(BaseRequest(token))
}
```
### Java
```java
    Config config = new Config("https://theoldreader.com", "Custom Java Client");
    Theoldreader theoldreader = new Theoldreader(config);

    // get current server status
    Status status = theoldreader.status();

    // login and get token
    ClientLogin clientLogin = theoldreader.getAuth().login(
            new ClientLoginRequest("somebody@somewhere.net", "password"));
    String token = clientLogin.getAuth();

    // get user info
    UserInfo info = theoldreader.getUsers().info(new BaseRequest(token));

    // get subscriptions
    SubscriptionList subscriptions = theoldreader.getSubscriptions().subscriptionList(new BaseRequest(token));

    // get unread item ids
    ItemIds itemIds = theoldreader.getItems().allItemIds(
            new ItemRequest(token, (Integer) null, (Integer) null, true));

    // get folders
    FolderList folders = theoldreader.getFolders().folderList(new BaseRequest(token));

    // get friends
    FriendList friends = theoldreader.getFriends().friendList(new BaseRequest(token));
```