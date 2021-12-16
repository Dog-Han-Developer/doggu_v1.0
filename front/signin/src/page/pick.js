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

  #onSubmit = e => {
    console.log("!")
    location.href = '/#';
  }

  render = () => {
    this.#container.innerHTML = this.#template({
      //userProfile: this.#data.store.userProfile,
      //posts: this.#data.store.userPosts,
    });
    
    this.#container.addEventListener('submit', this.#onSubmit);
  }
}
