import template from './pick.template';
import HorizontalScroll from 'react-scroll-horizontal'

export default class Pick {
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

  #on3DJS = e => {
    console.log("!!!!")
  }

  render = () => {
    this.#container.innerHTML = this.#template({
      //userProfile: this.#data.store.userProfile,
      //posts: this.#data.store.userPosts,
    });
    
    this.#container.addEventListener('submit', this.#on3DJS);
  }
}
