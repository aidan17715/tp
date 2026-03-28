#!/usr/bin/env bash

# change to script directory
cd "${0%/*}"

cd ..
./gradlew clean build -x test

cd text-ui-test

java -ea -jar $(find ../build/libs/ -name "*.jar" -type f | head -1) < input.txt > ACTUAL.TXT

cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

sed -i 's/[[:space:]]*$//' ACTUAL.TXT
sed -i 's/[[:space:]]*$//' EXPECTED-UNIX.TXT

diff -b -B EXPECTED-UNIX.TXT ACTUAL.TXT
if [ $? -eq 0 ]
then
    echo "Test passed!"
    exit 0
else
    echo "Test failed!"
    exit 1
fi
