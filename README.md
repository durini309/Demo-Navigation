# Telus navigation use cases

Currently, Telus' wifi app is using the [conductor](https://github.com/bluelinelabs/Conductor) library to handle every thing related to the navigation. This library gives developers the ability to add and remove entries to the backstack as needed, given the app's navigation a lot of flexibility.

This project was created with the intent of replicate all the possible navigation use cases, but using android's own [navigation library](https://developer.android.com/guide/navigation).

## Use cases

### **Opening a stack filled with fragments**

#### **Goal**
Pushing multiple fragments in one shot so the user sees the one on top only but they press back it should go through the whole stack.

#### **Solution**
This can't be handled by programatically pushing fragments into the backstack, but there's another way to achieve this. In this project there are 4 different fragments: `FragmentA`, `FragmentB`, `FragmentC` and `FragmentD`. All these fragments have the following navigation flow:
<figure>
    <img src="https://user-images.githubusercontent.com/13813905/193364060-51625efa-c624-40d4-8d4c-64bb2fcd8b27.png" />
    <figcaption align = "center"><b>Fig.1 - Basic initial navigation</b></figcaption>
</figure>

The flow is pretty straightforward: we go from `A` all the way to `D` and, if the user clicks on the **Back to A** button placed on `D`, then we get back to `A` without keeping `B` and `C` on the backstack.

Now imagine we have a custom notification that, after the user clicks on it, it uses a deeplink that redirects to `D`. Given that this is the last fragment of the flow, the expected behaviour of the back button is to show `C`, then `B` and finally `A`. To achieve this behaviour, these steps were followed:

<u>Adding new actions</u>

First, actions that go from D -> C and C -> B were added. Each of this actions get advantage of the `popUpTo` and `popUpToInclusive` properties to remove theirselves out of the backstack. This is the sample code of the action that goes from `D` to `C`:

```xml
<!-- This action navigates to C, removing D from back stack -->
<action
    android:id="@+id/action_firstFlowDFragment_backTo_firstFlowCFragment"
    app:destination="@id/firstFlowCFragment"
    app:popUpTo="@id/firstFlowDFragment"
    app:popUpToInclusive="true"
/>
```

<u>Overriding onBackPress</u>

The recommended way to override the `onBackPress` event from a fragment is by using the [onBackPressedDispatcher](https://developer.android.com/reference/androidx/activity/OnBackPressedDispatcher) method. This works by adding a callback that will replace the `onBackPress` default behavior.

So, only to fragments `D` and `C`, we added a custom callback to their `onBackPressedDispatcher`:
```kotlin
requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
    if (args.fromNotification) {
        requireView().findNavController().navigate(
            FirstFlowDFragmentDirections.actionFirstFlowDFragmentBackToFirstFlowCFragment(
                fromNotification = true
            )
        )
        isEnabled = true
    } else {
        isEnabled = false
        requireActivity().onBackPressed()
    }
}
```

This callback will override the default behavior only if the `fromNotification` is set as `true`. We're using this argument as a flag to know whether the user reached the fragment via a notification or not. In this case, they expect to have `A`, `B` and `C` in the backstack. The behavior in the case this flag comes as `true` will be using an action to redirect to fragment `C`.

#### <u>Why this this callback not added to fragment B?</u>

The reason this callback is not needed on that fragment is because, after reaching fragment `D` via deeplink, by default Android adds the *home* fragment of the navgraph to the backstack, so after pressing back on `B` we'll have fragment `A`.

The final navigation graph ends up like this: 

<figure>
    <img src="https://user-images.githubusercontent.com/13813905/193365881-1ecb07e3-ee84-45ab-9a36-37ddfd750b27.png" />
    <figcaption align = "center"><b>Fig.2 - Navigation with custom back actions</b></figcaption>
</figure>

## Use cases

### **Navigating using deeplinks**

#### **Goal**
Once a user clicks on deeplink they should be taken to the respective screen with a condition of login. If not logged in they should first go to the login screen and sign in and then take to the respective deeplink screen.

#### **Solution**
This use case will be scoped just to the navigation logic, as the "log in" logic could be easilly handled by a view model. 

Jetpack Navigation makes it easy to use deeplinks. In our navigation graph, we just need to declare a deeplink for a specific fragment:


```xml
<fragment
    android:id="@+id/firstFlowDFragment"
    android:name="com.jcd.telusnavigation.first_flow.FirstFlowDFragment"
    android:label="fragment_first_flow_d"
    tools:layout="@layout/fragment_first_flow_d" >

    <deepLink app:uri="durini://telusnavigation.com/firstFlow/{from_notification}" />

    <argument
            android:name="from_notification"
            app:argType="boolean"
            android:defaultValue="false" />

    <!-- Actions declaration ... -->

</fragment>
```

So, if we want to navigate to that fragment, we can use an Intent like this one:

```kotlin
val intent = Intent(Intent.ACTION_VIEW).apply {
    data = "durini://telusnavigation.com/firstFlow/true".toUri()
    flags = Intent.FLAG_ACTIVITY_NEW_TASK
}
```

If we now try to open that fragment with that intent, it won't work as expected, as our application won't know which activity should handle that deeplink. To solve this, we need add a line in our activity's manifest declaration:

```xml
<activity
    android:name=".first_flow.FirstFlowActivity"
    android:exported="true">
    <nav-graph android:value="@navigation/first_flow_graph"/>
</activity>
```

Now, in our Fragment declaration, we can get the parameters sent in the URL by using our navArgs:

```kotlin
private val args: FirstFlowDFragmentArgs by navArgs()

// We can access our property like this
args.fromNotification
```

After all these steps are handled, then the deeplink should work as expected. Please keep in mind that, after a deeplink is opened, the start destination of that navigation graph is also added to the stack. So, in our example, after opening fragment `D` with a deeplink, our stack will be:

```
| A | D | ← top of the stack
```
## Use cases

### **Opening an activity**

#### **Goal**
Open a new activity on top of an already existing stack of the current activity with some fragments. Document how the stack operates in this case.

#### **Solution**
To replicate this behaviour, you can navigate through the FirstFlow. After reaching fragment `D`, you'll see there's a "Second Flow" button at the bottom of the screen:


<figure align = "center">
    <img src="https://user-images.githubusercontent.com/13813905/193650893-107f6196-ef9a-4db7-a227-0733524cc5a3.png" width="300" />
    <figcaption><b>Fig.3 - FirstFlow's Fragment D</b></figcaption>
</figure>

This button will click a new Activity, `SecondFlowActivity`, that has their own nav graph. This is the first fragment you'll see: 

<figure align = "center">
    <img src="https://user-images.githubusercontent.com/13813905/193651627-a812c69e-24b9-4fb2-b92b-3e21d6985301.png" width="300" />
    <figcaption><b>Fig.4 - SecondFlow's Fragment A</b></figcaption>
</figure>

If you press the "back" button on the emulator, then first flow's fragment `D` will be shown again, and the backstack will be the same as it was before opening `SecondFlowActivity`.

## Use cases

### **Closing a stack**

#### **Goal**
Test the ability of closing multiple fragments in one shot.

#### **Solution**
As of now, there's no way we can explicitly remove multiple fragments on demand, but Jetpack Navigation provides a way so that every time we move from one fragment to another, we can "pop" some fragments during the navigation. To achive this, android gives us 2 properties that can be set on actions: `popUpTo` and `popUpToInclusive`. Following the flow showed on **Fig.1 - Basic initial navigation**, we have this action that navigates from `D` to `A`:

```xml
<action
    android:id="@+id/action_firstFlowDFragment_to_firstFlowAFragment"
    app:destination="@id/firstFlowAFragment"
/>
```

If we leave the action like this, our task stack will be like this after navigating to `D`:

```
| A | B | C | D | A | ← top of the stack
```

So, if we press the back button on `A`, then `D` will be shown again, then `C` and so on...

In our case, this is not the expected behavior, as the reason we navigate from `D` to `A` was because we wanted to get back to the beginning of the flow. So, we expect the stack to be like this:

```
| A | ← top of the stack
```

To achive this, we will need to set the properties that were previously mentioned:

`app:popUpTo="@id/firstFlowAFragment"`: this tells android to automatically pop all the fragments in the back stack that are between the current fragment before navigation (`D`) and the destination fragment (`A`). So, this will make our stack to look like this:

```
| A | A | ← top of the stack
```

`app:popUpToInclusive="true"`: this tells android to also remove the new instance of the destination fragment from the stack, so that we don't keep 2 copies of the same fragment. So, in the end the stack will end up as expected:

```
| A | ← top of the stack
```

Our action will end up like this:

```xml
<action
    android:id="@+id/action_firstFlowDFragment_to_firstFlowAFragment"
    app:destination="@id/firstFlowAFragment"
    app:popUpTo="@id/firstFlowAFragment"
    app:popUpToInclusive="true"
/>
```

## Use cases

### **General lifecycle of stack**

#### **Goal**
Fully comprehend the general behavior of fragment's lifecycle based on the solutions proposed for the previous use cases

#### **Explanation**
The lifecycle of a fragment is as follows:

<figure align = "center">
    <img src="https://mohamedmoanes.files.wordpress.com/2018/07/1495079249_capture.png"/>
    <figcaption><b>Fig.5 - Fragment's lifecycle. Taken from <a href="https://mohamedmoanes.wordpress.com/2018/07/29/fragment-lifecycle/" target="_blank">this article</a></b></figcaption>
</figure>

With Jetpack Navigation library, using our First Flow as reference, when our fragment `A` is shown, these events are triggered:
```
FragmentA - onAttach
FragmentA - onCreate
FragmentA - onViewCreated
FragmentA - onStart
FragmentA - onResume
```

If we now navigate to fragment `B`, our these are triggered:

```
FragmentA - onPause
FragmentA - onStop
FragmentB - onAttach
FragmentB - onCreate
FragmentB - onViewCreated
FragmentB - onStart
FragmentA - onDestroyView
FragmentB - onResume
```

Lastly, if we navigate back from `B` to `home`, these will be triggered:

```
FragmentB - onPause
FragmentB - onStop
FragmentA - onViewCreated
FragmentA - onStart
FragmentB - onDestroyView
FragmentB - onDetach
FragmentA - onResume
```

You can notice that the neither `onAttach` nor `onCreate` events are called again on `FragmentA`. The lifecycle events start from `onViewCreated`. This is behavior when we navigate between fragments taking advantage of the backstack that is handled by android by default. 

But now, what happens if we create our "custom backstack" as it was proposed on [Opening a stack filled with fragments](#opening-a-stack-filled-with-fragments) use case?, as this scenario doesn't have fragments `B` or `C` on the backstack. So, if we directly navigate to fragment `D` by clicking a notification, then these events are triggered:

```
FragmentA - onAttach
FragmentA - onCreate
FragmentD - onAttach
FragmentD - onCreate
FragmentD - onViewCreated
FragmentD - onStart
FragmentD - onResume
```

As you can see, even though Fragment `A` is not shown to the user, it is being attached and created. This happens because the nav graph's home fragment is added to the backstack automatically, as discussed [here](#uwhy-this-this-callback-not-added-to-fragment-bu).

Now, if we press the back button on `D` expecting to see `C`, this events are called:

```
FragmentD - onPause
FragmentD - onStop
FragmentC - onAttach
FragmentC - onCreate
FragmentC - onViewCreated
FragmentC - onStart
FragmentD - onDestroyView
FragmentD - onDetach
FragmentC - onResume
```

You'll notice that `onAttach` and `onCreate` events from fragment `C` are being called, as this fragment was not originally on the backstack, and therefore, it was created from scratch.
