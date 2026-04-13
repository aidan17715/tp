#!/usr/bin/env bash

# change to script directory
cd "${0%/*}"

cd ..
./gradlew clean build

cd text-ui-test
# Reset persisted app state so repeated test runs remain deterministic.
rm -f data/recipes.json data/inventory.json

java -ea -jar ../build/libs/sudocookg.jar < input.txt > ACTUAL.TXT

cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT 2>/dev/null || true

sed -i '' 's/[[:space:]]*$//' ACTUAL.TXT
sed -i '' 's/[[:space:]]*$//' EXPECTED-UNIX.TXT

diff -b -B EXPECTED-UNIX.TXT ACTUAL.TXT
if [ $? -eq 0 ]
then
    echo "Test passed!"
    exit 0
else
    echo "Test failed!"
    exit 1
fi
