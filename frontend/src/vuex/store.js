import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import companies from "@/components/companies";
//import Axios from "axios";

Vue.use(Vuex)

const store = new Vuex.Store({
    state:{
    companies: [
    {
        "id": 1,
        "name": "first",
        "description": "some text",
        "logotype": "picture"
    },
    {
        "id": 2,
        "name": "second",
        "description": "some text",
        "logotype": "picture"
    },
    {
        "id": 3,
        "name": "third",
        "description": "some text",
        "logotype": "picture"
    },
    {
        "id": 4,
        "name": "fourth",
        "description": "some text",
        "logotype": "picture"
    },
    {
        "id": 5,
        "name": "fifth",
        "description": "some text",
        "logotype": "picture"
    },
    {
        "id": 6,
        "name": "six",
        "description": "some text",
        "logotype": "picture"
    }
]
}    ,
    actions: {
        GET_COMPANIES_FROM_API(){
            //return axios('http://localhost:3000/companies',
            //return axios('http://localhost:4200/companies',
            //    {
            //        method: 'GET'
            //    })
            //    .then((response) =>{
            //        commit('SET_COMPANIES_TO_VUEX', response.data)
            //    })
            return companies
        }
    },
    mutations:{
        SET_COMPANIES_TO_VUEX : (state, companies) => {
            state.companies = companies
        }
    },
    getters:{
         COMPANIES(state){
             return state.companies
         }
    }
})

export default store;