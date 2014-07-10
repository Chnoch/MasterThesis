from django.db import models


# Create your models here.
class Parameters(models.Model):
    model_id = models.IntegerField()
    noise = models.IntegerField(default=0)
    distribution_type = models.IntegerField(default=0)

    def __str__(self):
        return str(self.model_id)
