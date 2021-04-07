import Vue from "vue"
import App from "@/App.vue"
import Axios from 'axios'
import VueRouter from "vue-router"
import index from './components/index'
import regUser from './components/regUser'
import loginUser from './components/loginUser'
import companies from './components/companies'

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
	render: (h) => h(App),
}).$mount("#app");