<template>
    <md-card>
        <md-table-toolbar>
            <h1 class="md-title">OUR COMPANIES</h1>
        </md-table-toolbar>

        <v-table
            :companies_data=this.companies
        />

    </md-card>
</template>

<script>

document.title = "Companies";

// import {mapActions, mapGetters} from 'vuex'
import vTable from './table/v-table'

export default {
    name: "companies",
    data() {
        return {
            //companies: createFakeData()
            companies: this.getCompanies()
        }
    },
    components: {
        vTable
    },
    /*
    computed: {
        ...mapGetters([
            'COMPANIES'
        ])
    },
    methods: {
        ...mapActions([
            'GET_COMPANIES_FROM_API'
        ])
    },
    mounted() {
        this.GET_COMPANIES_FROM_API()
    }
    */
    methods: {
        getCompanies: function () {
            let data = [];
            var query = this.$http(
                {
                    method: 'get',
                    url: 'http://40.69.86.206:8082/company/10/0/',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function (response) {
                    console.log(response.data.content);
                    let content = response.data.content;
                    for (let i = 0; i < content.length; i++) {
                        data.push({
                            id: i,
                            name: content[i].name,
                            description: content[i].description,
                            logotype: content[i].logo
                        });
                    }
                    // data = response.data.content;
                    // console.log("data = " + data);
                    return data;
                })
                .catch(function (error) {
                    console.log(error);
                    return error;
                });

            query.then((response) => {
                // if error happened
                if (response.status != undefined) {
                    console.log(response);
                    let status = response.response.status;
                    if (status == 404) {
                        this.errorText = "Пользователя с такими данными не существует";
                    } else {
                        this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
                    }
                    return;
                }
                // console.log(response);
            });


            for (let i = 0; i < 100; i++) {
                data.push({
                    id: i,
                    name: "Company #" + i,
                    description: "bvlah blah",
                    logotype: "in dev"
                });
            }
            return data;
        }
    },
    mounted() {
        this.getCompanies();
    }
}
/*
function createFakeData() {
    let data = [];
    for (let i = 0; i < 100; i++) {
        data.push({
            id: i,
            name: "Company #" + i,
            description: "bvlah blah",
            logotype: "in dev"
        });
    }
    return data;
}
*/

</script>

<style>

.main-companies {
    margin: 0 auto;
    width: 500px;
    border: 1px solid #333;
}


body {
    background: #fcf8ec;
}

/*
h1 {
    font-family: "Berlin Sans FB";
    color: #456268;
    background: #d0e8f2;
}
*/
h4 {
    font-family: "Berlin Sans FB Demi";
    color: #79a3b1;
    background: #d0e8f2;
}

div {
    color: #79a3b1;
}

</style>

