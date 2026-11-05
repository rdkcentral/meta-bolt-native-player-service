SUMMARY = "Standalone native player for RDK-E"
DESCRIPTION = "A reference native  player for RDK-E"
HOMEPAGE = "https://github.com/rdkcentral/nativeplayerservice"
SECTION = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b17644be83787a32dcf1830fa4b0c64b"

PV = "0.1.1"

SRC_URI = "git://github.com/rdkcentral/nativeplayerservice;nobranch=1;protocol=https"
SRCREV = "0a2a7d3bcd415217eacce6b5a57e5e59d4ffacc3"


S = "${WORKDIR}/git"
DEPENDS = "rpcserver aamp gstreamer1.0 jsoncpp glib-2.0 firebolt-cpp-client"
RDEPENDS:${PN} = "rpcserver aamp"
inherit cmake pkgconfig

EXTRA_OECMAKE += "-DPRIVATE_CONNECTION=ON "

