## Conductor Extra 

Extra ❤️❤️❤️❤️ for [Conductor](https://github.com/bluelinelabs/Conductor)

Provides some basic building block and utility function for Conductor, some of it (like the `ChangeHandler`s is taken from Conductor demo itself) 

## Installation

We use [Jitpack](https://jitpack.io) so you have to add this to your root `build.gradle` 

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
	
and then add the dependency in your module `build.gradle`

```groovy
dependencies {
	compile 'com.github.esafirm:conductor-extra:x.y.z'
	
	// For Butterknife view binding
	compile 'com.github.esafirm.conductor-extra:conductor-extra-butterknife:x.y.z'
	
	// For Android Data Binding view binding
	compile 'com.github.esafirm.conductor-extra:conductor-extra-databinding:x.y.z'
}
```

Where `x.y.z` is the version you want to add. For the latest version you can look in release page or in this badge here 
[![](https://jitpack.io/v/esafirm/conductor-extra.svg)](https://jitpack.io/#esafirm/conductor-extra)


## Usage

If you prefer code than readme, please go for the [sample](https://github.com/esafirm/conductor-extra/tree/master/sample)

Use the view binding module if you don't want to write your own implementation of view binding

Currently there's two option

1. [Butterknife](#butterknife-view-binding)
2. [Android Data Binding Library](#android-data-binding-library-view-binding)

All of view binding modules have the same component name (e.g `AbsController`) and only separated by package name

### Butterknife View Binding

```kotlin
class MyController: AbsController(){
	
	@Bind(R.id.textview) lateinit var text: TextView
	
	override fun getLayoutResId(): Int = R.layout.your_layout
	
	override fun onViewBound(view: View){
		/*
           you don't have to call `Butterknife.bind` 
           or `Unbinder.unbind` again as it handled in `AbsController`
         */
         
         // do something with `text`
         text.visibility = View.GONE
	}
}	
```	
	
### Android Data Binding Library View Binding	

```kotlin
class MyController: AbsController(){

	override fun getLayoutResId(): Int = R.layout.your_layout
	
	override fun onViewBound(binding: YourLayoutBinding){
	
	//do something with your `ViewDataBinding`
	binding.text.visibility = View.GONE
	}
}
```	


## License

[MIT](https://github.com/esafirm/conductor-extra/blob/master/LICENSE)
	
