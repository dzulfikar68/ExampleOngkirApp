package io.github.dzulfikar68.exampleongkirapp

data class RajaOngkirResponse<T>(
		val rajaongkir: T? = null
)

data class ResultList<T>(
		val results: List<T>? = null
)

data class ProvinceItem(
		val province: String? = null,
		val province_id: String? = null
)

data class CityItem(
		val city_name: String? = null,
		val province: String? = null,
		val province_id: String? = null,
		val type: String? = null,
		val postal_code: String? = null,
		val city_id: String? = null
) {
	override fun toString(): String {
		return city_name ?: "-"
	}
}

data class OngkirItem(
		val costs: List<CostsItem>? = null,
		val code: String? = null,
		val name: String? = null
)

data class CostsItem(
		val cost: List<CostItem>? = null,
		val service: String? = null,
		val description: String? = null
)

data class CostItem(
		val note: String? = null,
		val etd: String? = null,
		val value: Int? = null
)

