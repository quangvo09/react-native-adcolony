
# react-native-rn-adcolony

## Getting started

`$ npm install react-native-rn-adcolony --save`

### Mostly automatic installation

`$ react-native link react-native-rn-adcolony`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-rn-adcolony` and add `RNRnAdcolony.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRnAdcolony.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.smartapp.rnadcolony.RNRnAdcolonyPackage;` to the imports at the top of the file
  - Add `new RNRnAdcolonyPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-rn-adcolony'
  	project(':react-native-rn-adcolony').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-rn-adcolony/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-rn-adcolony')
  	```


## Usage
```javascript
import RNRnAdcolony from 'react-native-rn-adcolony';

// TODO: What to do with the module?
RNRnAdcolony;
```
  