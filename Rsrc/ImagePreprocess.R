showDist <- function(vector,path){
  jpeg(file=path)
  bks <- seq(from=0,to=255,by=1)
  plot(hist(vector,breaks = bks))
  dev.off()
}

clusterOutputCSV = function(pathIn,pathOut,cens=60,maxCens = 100){
  data = read.csv(pathIn)
  cst= bclust(data,centers =cens,maxcluster = maxCens )
  cenMat = centers.bclust(cst,cens)
  cenFrame = data.frame(cenMat)
  write.csv(cenFrame,pathOut)
}
  