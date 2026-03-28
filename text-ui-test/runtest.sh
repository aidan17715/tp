#!/usr/bin/env bash

# change to script directory
cd "${0%/*}"

cd ..
./gradlew clean build -x test

cd text-ui-test

# Clean up persisted data before running test
rm -rf data/

java -ea -jar $(find ../build/libs/ -name "*.jar" -type f | head -1) < input.txt > ACTUAL.TXT

cp EXPECTED.TXT EXPECTED-UNIX.TXT

# Normalize line endings (works on both Mac and Linux)
if command -v dos2unix &> /dev/null; then
    dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT
fi

# Trim trailing whitespace (Mac-compatible sed)
sed -i '' 's/[[:space:]]*$//' ACTUAL.TXT 2>/dev/null || sed -i 's/[[:space:]]*$//' ACTUAL.TXT
sed -i '' 's/[[:space:]]*$//' EXPECTED-UNIX.TXT 2>/dev/null || sed -i 's/[[:space:]]*$//' EXPECTED-UNIX.TXT

diff -b -B EXPECTED-UNIX.TXT ACTUAL.TXT
if [ $? -eq 0 ]
then
    echo "Test passed!"
    exit 0
else
    echo "Test failed!"
    exit 1
fi
