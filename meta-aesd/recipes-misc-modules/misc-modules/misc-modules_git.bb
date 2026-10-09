# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/mikhailtchernychev-dev/aeld-assignment-7;protocol=https;branch=main \
           file://0001-preparing-for-yocto-build.patch \
           "

# Modify these as desired
PV = "1.0+git"
SRCREV = "9b0623facd6396c1c242e15d3a9814041c12fc38"

S = "${WORKDIR}/git"

inherit module
MODULES_MODULE_SYMVERS_LOCATION:append = "misc-modules"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
