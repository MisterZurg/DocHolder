import Vue from "vue"
import App from "@/App.vue"
import Axios from 'axios'
import VueRouter from "vue-router"
import index from './components/index'
import regUser from './components/regUser'
import loginUser from './components/loginUser'
import companies from './components/companies'
import registerTest from './components/registerUserForm'
import company from './components/company'
import profile from './components/profile'
//import vuetify from './plugins/vuetify';

import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default-dark.css'
import mainPage from "@/components/mainPage";
import mainPageAdmin from "@/components/mainPageAdmin";

Vue.use(VueMaterial)

import { library } from '@fortawesome/fontawesome-svg-core'
import { faHome, faCoffee, faInfoCircle, faUserPlus, faSignInAlt, faSignOutAlt, faPenSquare, faDownload, faTrash, faEye, faAt } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faHome, faCoffee, faInfoCircle, faUserPlus, faSignInAlt, faSignOutAlt, faPenSquare, faDownload, faTrash, faEye, faAt)

Vue.component('font-awesome-icon', FontAwesomeIcon)

// router
Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		component: mainPage
	},
	{
		path: "/mainPageAdmin",
		component: mainPageAdmin
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
		path: "/about",
		component: index,
		children: []
	},
	{
		path: "/test-reg-user",
		component: registerTest
	},
	{
		path: "/company",
		component: company
	},
	{
		path: "/profile",
		component: profile
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