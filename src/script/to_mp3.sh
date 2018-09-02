for f in ../../output/*_audiobook*; do ffmpeg -i $f -acodec libmp3lame -q:a 0 ${f%.*}.mp3; done

