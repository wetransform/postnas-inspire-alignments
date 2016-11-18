#!/bin/sh

rm rlp-inspire-alignments.zip
zip rlp-inspire-alignments.zip $(git ls-files)
