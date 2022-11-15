package gemastik.pendekar.utils

enum class ReportStatus(val title:String) {
    SUCCESS("Laporan\nBerhasil"),
    FAILURE("Laporan\nDibatalkan"),
    PROCESS("Laporan\nDiproses")
}