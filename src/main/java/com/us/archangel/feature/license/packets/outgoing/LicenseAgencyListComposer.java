package com.us.archangel.feature.license.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.CorpManager;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.roleplay.corp.LicenseType;
import com.us.roleplay.corp.LicenseMapper;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LicenseAgencyListComposer extends MessageComposer {

    private List<CorpModel> getLicenseAgencies() {
        LicenseType[] licenseTypes = LicenseType.values();

        List<CompletableFuture<List<CorpModel>>> futures = Arrays.stream(licenseTypes)
                .map(licenseType -> CompletableFuture.supplyAsync(() -> {
                    CorpIndustry corpIndustry = LicenseMapper.licenseTypeToCorpTag(licenseType);
                    if (corpIndustry == null) {
                        return null;
                    }
                    return CorpService.getInstance().findManyByIndustry(corpIndustry);
                }))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        ).join();
    }

    @Override
    protected ServerMessage composeInternal() {
        List<CorpModel> licenseAgencyCorps = this.getLicenseAgencies();
        this.response.init(Outgoing.licenseAgencyListComposer);
        this.response.appendInt(licenseAgencyCorps.size());
        for (CorpModel licenseAgency : licenseAgencyCorps) {
            this.response.appendString(licenseAgency.getId() + ";" + licenseAgency.getDisplayName() + ";" + LicenseMapper.corpIndustryToLicenseType(licenseAgency.getIndustry()));
        }
        return this.response;
    }
}
