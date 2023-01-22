package cz.sedlardavid.eventorr.entities

data class StatsX(
    val average_price: Int,
    val dq_bucket_counts: List<Int>,
    val highest_price: Int,
    val listing_count: Int,
    val lowest_price: Int,
    val lowest_price_good_deals: Any,
    val lowest_sg_base_price: Int,
    val lowest_sg_base_price_good_deals: Any,
    val median_price: Int,
    val visible_listing_count: Int
)