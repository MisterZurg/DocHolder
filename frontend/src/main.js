import Vue from "vue"
import App from "@/App.vue"
import Axios from 'axios'
import VueRouter from "vue-router"
import index from './components/index'
import regUser from './components/regUser'
import loginUser from './components/loginUser'
import companies from './components/companies'
import company from './components/company'
// import testOnly from './components/testOnly'
//import vuetify from './plugins/vuetify';

import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default-dark.css'
import mainPage from "@/components/mainPage";

Vue.use(VueMaterial)

import { library } from '@fortawesome/fontawesome-svg-core'
import { faCoffee, faInfoCircle, faUserPlus, faSignInAlt, faSignOutAlt } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faCoffee, faInfoCircle, faUserPlus, faSignInAlt, faSignOutAlt)

Vue.component('font-awesome-icon', FontAwesomeIcon)

// register jw pagination component globally
import JwPagination from 'jw-vue-pagination';
Vue.component('jw-pagination', JwPagination);

// router
Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		component: index
	},
	{
		path: "/reg-user",
		component: regUser
	},
	{
		path: "/login-user",
		component: loginUser
	},
    {
        path: "/companies",
        component: companies
    },
	{
		path: "/main-page",
		component: mainPage,
		children: []
	},
	{
		path: "/company",
		component: company
	}
];

const router = new VueRouter({
	routes,
	mode: "history",
});

// axios
Vue.prototype.$http = Axios;
const token = localStorage.getItem('token')
if (token) {
  Vue.prototype.$http.defaults.headers.common['Authorization'] = token
}

// vue
Vue.config.productionTip = false;

new Vue({
    router,
    //vuetify,
    render: (h) => h(App)
}).$mount("#app");