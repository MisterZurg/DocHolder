<template>
    <div>
        <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
            <md-table-toolbar>
                <div class="md-toolbar-section-start">
                    <h1 class="md-title">Companies</h1>
                </div>
            </md-table-toolbar>

            <md-table-row slot="md-table-row" slot-scope="{ item }">
                <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
<!--                <md-table-cell>-->
<!--                  <img src="data:image/jpeg;base64,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">-->

<!--                </md-table-cell>-->
                <md-table-cell md-label="Logo" >
                  <img :src="item.logotype">
                </md-table-cell>
                <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
                <md-table-cell md-label="Description" md-sort-by="description">{{ item.description }}</md-table-cell>
            </md-table-row>

        </md-table>
        <div class="v-table__pagination">
            <div class="page"
                 v-for="page in pageColumn"
                 :key="page"
                 :class="{'page_selected': page === pageNumber}"
                 @click="pageClick(page)"
            >{{ page }}
            </div>

            <md-button
                class="md-icon-button"
                :disabled="pageNumber === 1"
                @click="prevPage">
                <md-icon>keyboard_arrow_left</md-icon>
            </md-button>

            <md-button
                class="md-icon-button"
                :disabled="pageNumber >= pageColumn"
                @click="nextPage">
                <md-icon>keyboard_arrow_right</md-icon>
            </md-button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'TableSearch',
    data: () => ({
        search: null,
        searched: [],
        companiesPerPage: 4,
        pageNumber: 1,
        pageColumn: 1,
        companies_data: []

    }),
    mounted: function () {
        this.companies_data = this.getCompanies();
        this.pageColumn = this.getPageColumn();
        this.searched = this.companies_data;
    },
    methods: {
        getCompanies() {
            let data = [];
            this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company?limit=' + this.companiesPerPage + '&page=' + (this.pageNumber - 1),
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function (response) {
                    // console.log(response.data.content);
                    let content = response.data.content;
                    for (let i = 0; i < content.length; i++) {
                        data.push({
                            id: i,
                            name: content[i].name,
                            description: content[i].description,
                            logotype: "data:image/jpeg;base64," + content[i].logo
                        });
                    }
                    return data;
                })
                .catch(function (error) {
                    console.log("catch error : " + error);
                    return error;
                });
            return data;
        },

        getPageColumn() {
            var query = this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company/count',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function (response) {
                    let column = response.data;
                    return Math.ceil(column);
                })
                .catch(function (error) {
                    console.log("catch error : " + error);
                    // return error;
                });
            query.then((response) => {
                // if error happened
                if (response.status != undefined) {
                    console.log(response);
                    let status = response.response.status;
                    if (status == 404) {
                        this.errorText = "Компаний не существует";
                    } else {
                        this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
                    }
                    return;
                }
                this.pageColumn = Math.ceil(response / this.companiesPerPage);
            });
            return 1;
        },
        pageClick(page) {
            this.pageNumber = page;
            this.companies_data = this.getCompanies();
            this.searched = this.companies_data;
        },
        nextPage() {
            this.pageNumber++;
            this.companies_data = this.getCompanies();
            this.searched = this.companies_data;

        },
        prevPage() {
            this.pageNumber--;
            this.companies_data = this.getCompanies();
            this.searched = this.companies_data;

        }
    },

}
</script>

<style lang="scss" scoped>
.md-table{
    min-height: 550px;
}

.md-table-row {
    height: 100px !important;
}

.md-field {
    max-width: 300px;
}

.v-table__pagination {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    margin-top: 30px;
    margin-right: 5px;
}

.page {
    padding: 8px;
    cursor: pointer;
}

.page:hover {
    background: #42b983;
}

.page_selected {
    background: #2c3e50;
}

</style>