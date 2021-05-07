<template>
	<md-tabs>
		<md-tab id="tab-home" md-label="Описание">
			<description/>
		</md-tab>

			<md-tab id="tab-pages" md-label="Документы">
				<documents/>
			</md-tab>

			<md-tab id="tab-posts" md-label="Сотрудники">
				<employees/>
			</md-tab>
		</md-tabs>
</template>

<script>

import description from './company/description';
import documents from './company/documents';
import employees from './company/employees';

export default {
	components: {
		description,
		employees,
		documents
	},
	data() {
		return {
		}
	},
	computed: {
	},
	mounted: function() {
		console.log(localStorage);
		console.log(this.$route.query.id);
	},
	methods: {
	}
}
</script>

<style lang="scss">


</style>