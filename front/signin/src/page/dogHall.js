import React from 'react';
import {Canvas,useFrame} from '@react-three/fiber';
import template from './dogHall.template';
//import {OrbitControls} from '@react-three/drei';

export default class dogHall{
  #template = template;
  #data;
  #container;

  constructor(container, data) {
    this.#container = document.querySelector(container);
    this.#data = data;

    this.#initialize();
  }

  #initialize = () => {
    
  }

  render = () => {
    this.#container.innerHTML = this.#template({
      //userProfile: this.#data.store.userProfile,
      //posts: this.#data.store.userPosts,
    });
  }
}