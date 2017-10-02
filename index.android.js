/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  Button,
  NativeModules,
  View
} from 'react-native';

module.exports = NativeModules.ActivityStarter;
const TestModule = NativeModules.ActivityStarter

export default class Poc_nativex_2 extends Component {
  render() {
    return (
      <View style={styles.container}>
	    <Button title="game lauch" onPress={() => NativeModules.ActivityStarter.redirectToActivity('136149','game','nativex')}/>
		<Button title="Open store" onPress={() => NativeModules.ActivityStarter.redirectToActivity('136149','store','nativex')}/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('Poc_nativex_2', () => Poc_nativex_2);
