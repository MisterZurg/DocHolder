<template>

        <!-- <div id="app"><router-view /> -->
        <!--
        <div class="header">
            <toggler></toggler>
            <div class="header-title">
                <router-link to='/'> DocHolder </router-link><br>
            </div>
            <div class="header-search">
                <input type="text" placeholder="Введите запрос...">
                <div class="my-2">
                    <v-btn
                        color="primary"
                        fab
                        small
                        dark
                    >
                        <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                </div>
            </div>
            <div class="header-authorization" ref="headerAuthorization">
                <router-link to='/login-user'>
                    <v-btn>Войти
                        <v-icon>mdi-login</v-icon>
                    </v-btn>
                </router-link>
                <router-link to='/reg-user'>
                    <v-btn >
                        Регистрация
                        <v-icon>mdi-account_circle</v-icon>
                    </v-btn>
                </router-link>
            </div>
            <div class="header-account" ref="headerAccount">
                <router-link to='/account'>
                    {{name}} {{surname}}
                </router-link>
                <v-btn v-on:click="logout"
                    color="pink"
                    elevation="2"
                ><v-icon>mdi-logout</v-icon>
                    Выйти</v-btn>
                    -->
                <!--<button v-on:click="logout">Выйти</button>-->
        <!--
            </div>
        </div>
        -->
        <!-- <HelloWorld msg="Home page"/> -->
        <!--
        <transition name="fade">
            <router-view />
        </transition>
        -->
       <!-- <bottom-bar/>
    </div>-->
    <div class="page-container">
        <md-app md-waterfall md-mode="overlap">
            <md-app-toolbar class="md-primary md-large">
                <div class="md-toolbar-row">
                    <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
                        <md-icon>menu</md-icon>
                    </md-button>

                    <span class="md-title">DocHolder</span>
                </div>
            </md-app-toolbar>

            <md-app-drawer :md-active.sync="menuVisible">
                <md-toolbar class="md-transparent" md-elevation="0">
                    Navigation
                </md-toolbar>

                <md-list>
                    <md-list-item>
                        <font-awesome-icon icon="user-plus" />
                        <router-link to='/reg-user'>
                            <span class="md-list-item-text">Sign Up</span>
                        </router-link>
                    </md-list-item>

                    <md-list-item>
                        <font-awesome-icon icon="sign-in-alt" />
                        <router-link to='/login-user'>
                            <span class="md-list-item-text">Sign In</span>
                        </router-link>
                    </md-list-item>

                    <md-list-item>
                        <font-awesome-icon icon="info-circle" />
                        <router-link to='/'>
                            <span class="md-list-item-text">About</span>
                        </router-link>
                    </md-list-item>
                </md-list>
            </md-app-drawer>

            <md-app-content>
                <!-- Dynamically update content -->
               <router-view/>
            </md-app-content>
        </md-app>
        <font-awesome-icon icon="user-secret" />
    </div>
</template>

<script>

export default {
    name: 'App',
    data: () => ({
        menuVisible: false
    }),
    // data() {
    //     return{
    //         name: "",
    //         surname: ""
    //     }
    // },
    mounted: function () {
        this.login();
    },
    methods: {
        // is user authorized
        login: function (){
            if(localStorage.getItem('id') != null){
                // if token had expired we should logout user
                let timeNow = Math.floor(Date.now() / 1000);
                if(timeNow > localStorage.getItem('exp')){
                    this.logout();
                    return;
                }
                // change header style
                this.name = localStorage.getItem('name');
                this.surname = localStorage.getItem('surname');
                this.$refs.headerAuthorization.style.display = "none";
                this.$refs.headerAccount.style.display = "flex";
            }
        },
        // logout user if logout button had clicked or JWT expired
        logout: function (){
            localStorage.clear();
            this.$refs.headerAuthorization.style.display = "flex";
            this.$refs.headerAccount.style.display = "none";
            this.$router.push('/').catch(()=>{});
        }
    }
}
</script>

<style>



</style>