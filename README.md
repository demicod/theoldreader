# theoldreader
The Old Reader API library for Kotlin

### Build
`gradlew jar`

### Usage
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
