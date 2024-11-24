#!/bin/bash

find . -name "*.java" -print0 | while IFS= read -r -d '' file; do
  echo "=== $file ==="
  cat "$file"
done > source.txt
