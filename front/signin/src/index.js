import Store from './store';
import Login from './page/login';
import Profile from './page/profile';
import PageNotFound from './page/page-not-found';
import Singup from './page/signup';
import Pick from './page/pick';
import Doginfo from './page/doginfo';

const store = new Store();

function router() {
  const path = location.hash;

  switch(path) {
    case '':
    case '#/login':
      const login = new Login('#root', {
        store,
        title: 'JS & TS Essential'
      });
      login.render();
      break;
    case '#/profile':
      const profile = new Profile('#root', { store });
      profile.render();
      break;
    case '#/signup':
      const signup = new Singup('#root', { store });
      signup.render();
      break;
    case '#/doginfo':
      const doginfo = new Doginfo('#root', { store });
      doginfo.render();
      break;
    case '#/pick':
      const pick = new Pick('#root', { store });
      pick.render();
      break;
    default:
      const pageNotFound = new PageNotFound('#root');
      pageNotFound.render();
      break;
  }
}

window.addEventListener('hashchange', router);
document.addEventListener('DOMContentLoaded', router);
